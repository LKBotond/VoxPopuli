import { basePath } from "../../contracts/Path";
import type { AuthHeader } from "../../contracts/ApiRequest";
import type { ApiRequest } from "../../contracts/ApiRequest";
export const GET = "GET";
export const PUT = "PUT";
export const POST = "POST";
export const DELETE = "DELETE";

export async function post<THeader extends HeadersInit, TPayload, TResponse>(
  path: string,
  request: ApiRequest<THeader, TPayload>,
): Promise<TResponse> {
  const response = await fetch(basePath + path, {
    method: POST,
    headers: request.headers,
    body: JSON.stringify(request.payload),
  });

  if (!response.ok) {
    throw new Error("API request failed: " + response.status);
  }

  return response.json() as Promise<TResponse>;
}

export async function put<
  Theader extends Record<string, string>,
  Tpayload,
  Tresponse,
>(path: string, payload: ApiRequest<Theader, Tpayload>): Promise<Tresponse> {
  const response = await fetch(basePath + path, {
    method: PUT,
    headers: payload.headers,
    body: JSON.stringify(payload.payload),
  });
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  return response.json();
}
export async function get<T_Response>(
  path: string,
  headers: AuthHeader,
): Promise<T_Response> {
  const response = await fetch(basePath + path, {
    method: GET,
    headers: headers,
  });
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  return response.json();
}
export async function del<Tresponse>(
  path: string,
  headers: AuthHeader,
): Promise<Tresponse | void> {
  const response = await fetch(basePath + path, {
    method: DELETE,
    headers: headers,
  });
  if (!response.ok) {
    throw new Error("API request failed, " + response.status);
  }
  if (response.status === 204) {
    return;
  }
  return response.json();
}
