import { Session } from "../types/Session";
export function collectFormData(formId: string) {
  const form = document.getElementById(formId) as HTMLFormElement;
  return new FormData(form);
}

export function validateFormdata(data: FormData) {
  let incomplete: boolean = false;
  data.forEach((value, key) => {
    if (!value || value.toString().trim() === "") incomplete = true;
  });
  if (incomplete) {
    return false;
  }
  return true;
}

export async function saveSession(sessionToken: Session) {
  await chrome.storage.session.set({ session: sessionToken });
}

export async function loadSession(): Promise<Session | undefined> {
  const { session } = await chrome.storage.session.get("session");
  return session as Session | undefined;
}

export function setPage(path: string) {
  chrome.action.setPopup({ popup: path });
}
export function redirect(path: string) {
  window.location.href = path;
}
