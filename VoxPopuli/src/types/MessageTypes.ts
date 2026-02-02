import {
  LoginRequest,
  SessionToken,
  CommentRequest,
  CommentResponse,
  RegistrationRequest,
} from "./VoxPopuliTypes";

// Base type
export interface BaseMessage<Action extends string, Payload = undefined> {
  action: Action;
  payload: Payload;
}

// Auth
export type LoginMessage = BaseMessage<"login", LoginRequest>;
export type RegistrationMessage = BaseMessage<"register", RegistrationRequest>;

// Union type for all messages
export type RuntimeMessage =
  | LoginMessage
  | RegistrationMessage
