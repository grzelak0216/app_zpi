const apiProtocol = process.env.REACT_APP_API_PROTOCOL || 'http';
const apiHost = process.env.REACT_APP_API_HOST || 'localhost';
const apiPort = process.env.REACT_APP_API_PORT || '8081';

const HOST_API = `${apiProtocol}://${apiHost}:${apiPort}`;
const HOST = HOST_API || 'http://localhost:8081';

export default HOST;
