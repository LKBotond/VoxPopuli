import { sendMessage } from "@/entrypoints/common/service/frontend/Messaging";
import { hashString } from "@/entrypoints/common/utils/hash";
import type { CommentResponse,CommentRequest } from "@/entrypoints/common/contracts/Comment";

import * as Actions from "@/entrypoints/common/types/Actions";

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
    return sourceLink;
  }
}

export async function getSourceLinkHash(sourceLink: string) {
  return await hashString(cleanSourceLink(sourceLink));
}

export async function getAllCommentsForPage(
  urlHex: string,
): Promise<CommentResponse[]> {
  return sendMessage({
    action: Actions.GET_COMMENTS,
    payload: urlHex,
  });
}

export async function getUserAlias(): Promise<string> {
  return sendMessage({
    action: Actions.GET_ALIAS,
    payload: null,
  });
}

export function buildCommentRequest(
  parentId: string | undefined,
  content: string,
  userAlias: string,
  sourceLinkHash: string,
): CommentRequest {
  return {
    parentId,
    content,
    alias: userAlias,
    sourceLinkHash: sourceLinkHash,
    updatedAt: new Date().toISOString(),
  };
}
