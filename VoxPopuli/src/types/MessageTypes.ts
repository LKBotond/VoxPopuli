import {
  LoginRequest,
  SessionToken,
  CommentRequest,
  CommentResponse,
  RegistrationRequest,
  CommentEditRequest,
} from "./VoxPopuliTypes";

// Base type
export interface BaseMessage<Action extends string, Payload = undefined> {
  action: Action;
  payload: Payload;
}

// Auth
export type LoginMessage = BaseMessage<"login", LoginRequest>;
export type LogoutMessage = BaseMessage<"logout">;
export type RegistrationMessage = BaseMessage<"register", RegistrationRequest>;

//comment
export type CommentGetMessage = BaseMessage<"getComments", string>;
export type CommentMessage = BaseMessage<"comment", CommentRequest>;
export type CommentEditMessage = BaseMessage<"edit", CommentEditRequest>;
export type CommentDeleteMessag = BaseMessage<"deleteComment", string>;
export type UnknownActionResponse = BaseMessage<
  string,
  { error: "Unknown action" }
>;

// Union type for all messages
export type RuntimeMessage =
  | LoginMessage
  | RegistrationMessage
  | CommentGetMessage
  | CommentMessage
  | CommentEditMessage
  | CommentDeleteMessag
  | UnknownActionResponse;
