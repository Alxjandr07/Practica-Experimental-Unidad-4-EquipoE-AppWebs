// Servidor estatico ligero con compresion gzip para mediciones Lighthouse.
// Replica un contenedor de produccion (nginx con gzip) de forma reproducible.
// Ademas reenvia /api/* al backend local para que la app funcione detras de
// un unico origen (localhost o tunel ngrok) sin CORS.
const http = require('http');
const fs = require('fs');
const path = require('path');
const zlib = require('zlib');

const ROOT = path.join(__dirname, 'dist', 'sgroas-frontend', 'browser');
const PORT = process.env.PORT || 4200;
const API_TARGET = process.env.API_TARGET || 'http://localhost:8080';
const TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.ico': 'image/x-icon',
  '.png': 'image/png',
  '.svg': 'image/svg+xml',
};

const proxyApi = (req, res) => {
  const target = new URL(API_TARGET);
  const opts = {
    hostname: target.hostname,
    port: target.port || 80,
    method: req.method,
    path: req.url,
    headers: { ...req.headers, host: `${target.hostname}:${target.port}` },
  };
  const upstream = http.request(opts, (up) => {
    res.writeHead(up.statusCode, up.headers);
    up.pipe(res);
  });
  upstream.on('error', () => {
    res.writeHead(502, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ message: 'Backend no disponible' }));
  });
  req.pipe(upstream);
};

http
  .createServer((req, res) => {
    if (req.url.startsWith('/api/')) {
      proxyApi(req, res);
      return;
    }
    let urlPath = decodeURIComponent(req.url.split('?')[0]);
    if (urlPath === '/') urlPath = '/index.html';
    let file = path.join(ROOT, urlPath);
    if (!file.startsWith(ROOT)) {
      res.writeHead(403);
      res.end();
      return;
    }
    fs.stat(file, (err, stat) => {
      if (err || !stat.isFile()) {
        res.writeHead(404);
        res.end('Not found');
        return;
      }
      const ext = path.extname(file).toLowerCase();
      res.setHeader('Content-Type', TYPES[ext] || 'application/octet-stream');
      res.setHeader('Cache-Control', 'public, max-age=31536000, immutable');
      const accept = (req.headers['accept-encoding'] || '').includes('gzip');
      if (accept) {
        res.setHeader('Content-Encoding', 'gzip');
        const gz = zlib.createGzip();
        res.writeHead(200);
        fs.createReadStream(file).pipe(gz).pipe(res);
      } else {
        res.writeHead(200);
        fs.createReadStream(file).pipe(res);
      }
    });
  })
  .listen(PORT, () => {
    console.log(`Static server (gzip) en http://localhost:${PORT} sirviendo ${ROOT}`);
  });
