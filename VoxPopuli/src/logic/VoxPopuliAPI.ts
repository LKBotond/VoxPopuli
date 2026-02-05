import {
  CommentEditRequest,
  CommentRequest,
  CommentResponse,
  LoginRequest,
  RegistrationRequest,
  SessionToken,
  AuthHeaders,
  SessionHeaders,
} from "../types/VoxPopuliTypes";
import { jsonify } from "../helpers/helpers";

// Constants
const BASE_URL: string = "url";
const API_PATH: string = "/api/v1";
const EXTENSION_ID: string =
  "chromeExtensionIdNotHardcodedHereButInEnvironmentalVariables";
const ORIGIN_HEADER = createOriginHeaders(EXTENSION_ID);

async function call<T>(
  target: string,
  method: string,
  inputHeaders: HeadersInit,
  body?: BodyInit, // <-- new optional parameter
): Promise<T> {
  const response = await fetch(BASE_URL + API_PATH + target, {
    method,
    headers: inputHeaders,
    body,
  });

  if (!response.ok) {
    throw new Error(`HTTP error ${response.status}`);
  }

  return (await response.json()) as T;
}
export async function login(
  loginRequest: LoginRequest,
): Promise<SessionToken | undefined> {
  return await call(
    "/auth/login",
    "POST",
    ORIGIN_HEADER,
    jsonify(loginRequest),
  );
}
export async function register(
  registrationRequest: RegistrationRequest,
): Promise<SessionToken | undefined> {
  return await call(
    "/auth/register",
    "POST",
    ORIGIN_HEADER,
    jsonify(registrationRequest),
  );
}

export async function getComments(
  sourcelInkHash: string,
  sessionToken: SessionToken,
): Promise<CommentResponse[]> {
  const sessionHeaders = createSessionHeaders(EXTENSION_ID, sessionToken);
  return await call("/comments/" + sourcelInkHash, "GET", sessionHeaders);
}

export async function postComment(
  commentRequest: CommentRequest,
  sessionToken: SessionToken,
): Promise<CommentResponse | undefined> {
  const sessionHeaders = createSessionHeaders(EXTENSION_ID, sessionToken);
  return await call(
    "/comments",
    "POST",
    sessionHeaders,
    jsonify(commentRequest),
  );
}

export async function editComment(
  commentEditRequest: CommentEditRequest,
  sessionToken: SessionToken,
): Promise<CommentResponse | undefined> {
  const sessionHeaders = createSessionHeaders(EXTENSION_ID, sessionToken);
  return await call(
    "/comments",
    "PUT",
    sessionHeaders,
    jsonify(commentEditRequest),
  );
}

export async function deleteComment(
  commentId: string,
  sessionToken: SessionToken,
): Promise<CommentResponse> {
  const sessionHeaders = createSessionHeaders(EXTENSION_ID, sessionToken);
  return await call("/comments/" + commentId, "DELETE", sessionHeaders);
}
function createOriginHeaders(extensionId: string): AuthHeaders {
  return { "X-extension-id": extensionId };
}

function createSessionHeaders(
  extensionId: string,
  session: SessionToken,
): SessionHeaders {
  return {
    ...createOriginHeaders(extensionId),
    "X-session-id": session.sessionId,
    "X-alias": session.alias,
  };
}
