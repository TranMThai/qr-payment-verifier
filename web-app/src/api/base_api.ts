// export const BASE_API = "http://localhost:8080"
export const BASE_API = "https://unnapped-thumbless-ace.ngrok-free.app"
export const NOTIFICATION_SOCKET = `${httpToWs(BASE_API)}/api/ws/notification`;

function httpToWs(endpoint: string): string {
  return endpoint;
}
