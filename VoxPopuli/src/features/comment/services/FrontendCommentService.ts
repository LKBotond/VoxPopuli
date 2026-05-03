import { sendMessage } from "../../../shared/api/frontend/Messaging";
import { hashString } from "../../../shared/utils/hash";
import type { CommentResponse } from "../../../shared/contracts/Comment";
import * as Actions from "../../../shared/api/frontend/Actions";

function cleanSourceLink(sourceLink: string) {
  try {
    const url = new URL(sourceLink);
    url.hostname = url.hostname.replace(/^www\./, "").toLowerCase();
    url.hash = "";
    const allowedParams = new Set(["id", "v"]);

    const newSearchParams = new URLSearchParams();
    for (const [key, value] of url.searchParams.entries()) {
      if (allowedParams.has(key)) {
        newSearchParams.set(key, value);
      }
    }
    url.search = newSearchParams.toString();
    url.pathname = url.pathname.replace(/\/+$/, "");
    return url.toString();
  } catch {
    return sourceLink; // fallback if invalid URL
  }
}

export async function getSourceLinkHash(sourceLink: string) {
  return await hashString(cleanSourceLink(sourceLink));
}

export async function getAllCommentsForPage(
  urlHex: string,
): Promise<CommentResponse[]> {
  return sendMessage<CommentResponse[]>({
    action: Actions.GET_COMMENTS,
    payload: urlHex,
  });
}

export async function getUserAlias(): Promise<string> {
  return sendMessage<string>({
    action: Actions.GET_ALIAS,
    payload: null,
  });
}
