// export const BASE_API = "http://localhost:8080"
export const NOTIFICATION_SOCKET = `${httpToWs(BASE_API)}/api/ws/notification`;

function httpToWs(endpoint: string): string {
  return endpoint;
}
