import { LoginRequest, RegistrationRequest } from "../contracts/Auth";
import { CommentRequest, CommentEditRequest } from "../contracts/Comment";
// Base type
export type BaseMessage<
  Action extends string,
  Payload = undefined,
> = Payload extends undefined
  ? { action: Action }
  : { action: Action; payload: Payload };
// Auth
export type LogoutMessage = BaseMessage<"logout">;
export type LoginMessage = BaseMessage<"login", LoginRequest>;
export type RegistrationMessage = BaseMessage<"register", RegistrationRequest>;

//comment
export type CommentGetMessage = BaseMessage<"getComments", string>;
export type CommentMessage = BaseMessage<"comment", CommentRequest>;
export type CommentEditMessage = BaseMessage<"edit", CommentEditRequest>;
export type CommentDeleteMessag = BaseMessage<"deleteComment", string>;

//errorHandling
export type Unauthorized = BaseMessage<string>;
export type UnknownActionResponse = BaseMessage<
  string,
  { error: "Unknown action" }
>;

//intra extension
export type GetAliasMessage = BaseMessage<"getAlias", null>;

// Union type for all messages
export type RuntimeMessage =
  | LoginMessage
  | LogoutMessage
  | RegistrationMessage
  | CommentGetMessage
  | CommentMessage
  | CommentEditMessage
  | CommentDeleteMessag
  | GetAliasMessage;
