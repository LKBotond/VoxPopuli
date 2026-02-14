export interface LoginRequest {
  email: string;
  pass: string;
}

export interface RegistrationRequest {
  email: string;
  alias: string;
  passArray: string;
}

export interface SessionToken {
  sessionId: string;
  alias: string;
}
