// Servidor estatico ligero con compresion gzip para mediciones Lighthouse.
// Replica un contenedor de produccion (nginx con gzip) de forma reproducible.
const http = require('http');
const fs = require('fs');
const path = require('path');
const zlib = require('zlib');

const ROOT = path.join(__dirname, 'dist', 'sgroas-frontend', 'browser');
const PORT = process.env.PORT || 4200;
const TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.ico': 'image/x-icon',
  '.png': 'image/png',
  '.svg': 'image/svg+xml',
};

http
  .createServer((req, res) => {
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
