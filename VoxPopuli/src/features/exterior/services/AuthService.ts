import type {
  LoginRequest,
  RegistrationRequest,
  SessionToken,
} from "../../../shared/contracts/Auth";
import type {
  ApiRequest,
  AuthHeader,
  OriginHeader,
} from "../../../shared/contracts/ApiRequest";
import { saveSession, endSession } from "../../../shared/utils/helpers";
import { del, post } from "../../../shared/api/backend/VoxPopuliApi";

export async function handleReregister(
  registrationRequest: ApiRequest<OriginHeader, RegistrationRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<
      OriginHeader,
      RegistrationRequest,
      SessionToken
    >("/auth/register", registrationRequest);
    await saveSession(sessionToken);
    chrome.action.setPopup({ popup: chrome.runtime.getURL("interior.html") });
    return true;
  } catch (e) {
    console.error("error: " + e);
    return false;
  }
}
export async function handleLogin(
  registrationRequest: ApiRequest<OriginHeader, LoginRequest>,
): Promise<boolean> {
  try {
    const sessionToken = await post<OriginHeader, LoginRequest, SessionToken>(
      "/auth/login",
      registrationRequest,
    );
    saveSession(sessionToken);
    chrome.action.setPopup({ popup: chrome.runtime.getURL("interior.html") });
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}

export async function handleLogout(
  logoutRequest: AuthHeader,
): Promise<boolean> {
  try {
    await del("/auth/logout", logoutRequest);
    await endSession();
    chrome.action.setPopup({ popup: chrome.runtime.getURL("index.html") });
    return true;
  } catch (e) {
    console.log("error: " + e);
    return false;
  }
}
