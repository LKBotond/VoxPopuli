import type {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "../contracts/Auth";
import { saveSession } from "../utils/helpers";
import { post } from "./VoxPopuliApi";
export async function register(
  registrationRequest: RegistrationRequest,
): Promise<boolean> {
  try {
    const sessionToken = await post<RegistrationRequest, SessionToken>(
      "/register",
      registrationRequest,
    );
    saveSession(sessionToken);
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}
export async function login(
  registrationRequest: LoginRequest,
): Promise<boolean> {
  try {
    const sessionToken = await post<LoginRequest, SessionToken>(
      "/login",
      registrationRequest,
    );
    saveSession(sessionToken);
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}

