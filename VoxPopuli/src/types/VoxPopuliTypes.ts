export type SessionToken = {
  sessionId: string;
  alias: string;
};
export type LoginRequest = {
  email: string;
  pass: string;
};
export type RegistrationRequest = {
  email: string;
  alias: string;
  passArray: string;
};
export type PassUpdateRequest = {
  newPass: string;
  oldPass: string;
};
export type CommentRequest = {
  parentId: string;
  content: string;
  sourceLinkHash: string;
  updatedAt: string;
};
export type CommentEditRequest = {
  commentId: string;
  editedContent: string;
};
export type CommentResponse = {
  commentId: string;
  parentId: string;
  content: string;
  updatedAt: string;
};
export type AuthHeaders = {
  "X-extension-id": string;
};
export type SessionHeaders = AuthHeaders & {
  "X-session-id": string;
  "X-alias": string;
};

