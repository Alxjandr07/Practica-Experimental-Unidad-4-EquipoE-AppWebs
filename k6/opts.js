export const opciones = {
  vus: 50,
  duration: '30s',
  thresholds: {
    http_req_duration: [
      { threshold: 'p(95)<200', abortOnFail: false },
    ],
    http_req_failed: [
      { threshold: 'rate<0.01', abortOnFail: false },
    ],
  },
  summaryTrendStats: ['avg', 'med', 'min', 'max', 'p(50)', 'p(90)', 'p(95)', 'p(99)'],
};
