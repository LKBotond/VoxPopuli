import { useState, useEffect } from "react";
import { sendMessage } from "@/entrypoints/common/service/frontend/Messaging";
import { hashString } from "@/entrypoints/common/utils/hash";
import type { CommentResponse } from "@/entrypoints/common/contracts/Comment";

export function useVoxPopuliData() {
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [userAlias, setAlias] = useState<string>("");
  const [sourceLinkHash, setSourceLinkHash] = useState<string>("");

  useEffect(() => {
    const init = async () => {
      const hexUrl = await hashString(window.location.href);
      setSourceLinkHash(hexUrl);

      const commentResponse = await sendMessage<
        { action: "getComments"; payload: string },
        CommentResponse[]
      >({
        action: "getComments",
        payload: hexUrl,
      });
      setComments(commentResponse);

      const aliasResponse = await sendMessage<{ action: "getAlias" }, string>({
        action: "getAlias",
      });
      setAlias(aliasResponse);
    };

    init();
  }, []);
  const addComment = (comment: CommentResponse) => {
    setComments((prev) => [...prev, comment]);
  };
  return { comments, userAlias, sourceLinkHash, addComment };
}
