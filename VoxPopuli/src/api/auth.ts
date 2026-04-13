import type {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "../contracts/Auth";
import type { ApiRequest, OriginHeader } from "../contracts/ApiRequest";
import { saveSession } from "../utils/helpers";
import { post } from "./VoxPopuliApi";

export async function register(
  registrationRequest: ApiRequest<OriginHeader, RegistrationRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<
      OriginHeader,
      RegistrationRequest,
      SessionToken
    >("/auth/register", registrationRequest);
    await saveSession(sessionToken);
    return true;
  } catch (e) {
    console.error("error: " + e);
    return false;
  }
}
export async function login(
  registrationRequest: ApiRequest<OriginHeader, LoginRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<OriginHeader, LoginRequest, SessionToken>(
      "/auth/login",
      registrationRequest,
    );
    saveSession(sessionToken);
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}
