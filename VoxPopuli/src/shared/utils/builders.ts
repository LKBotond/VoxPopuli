import type {
  AuthHeader,
  OriginHeader,
  ApiRequest,
} from "../contracts/ApiRequest";
import {
  CONTENT_TYPE,
  SESSION_HEADER,
  EXTENSION_ID,
} from "../contracts/NamingConventions";
export function buildAuthHeader(
  sessionId: string,
  extensionId: string,
): AuthHeader {
  return {
    [CONTENT_TYPE]: "application/json",
    [SESSION_HEADER]: sessionId,
    [EXTENSION_ID]: extensionId,
  };
}
export function buildOriginHeader(extensionId: string): OriginHeader {
  return {
    [CONTENT_TYPE]: "application/json",
    [EXTENSION_ID]: extensionId,
  };
}
export function buildApiRequest<header, payload = undefined>(
  headers: header,
  jsonified?: payload,
): ApiRequest<header, payload> {
  const apiRequest: ApiRequest<header, payload> = {
    headers: headers,
    payload: jsonified,
  };
  return apiRequest;
}
