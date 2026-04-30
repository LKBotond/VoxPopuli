import { useState, useEffect } from "react";
import { sendMessage } from "../api/VoxPopuliApi";
import { hashString } from "../utils/hash";
import type { CommentResponse } from "../contracts/Comment";

export function useVoxPopuliData() {
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [userAlias, setAlias] = useState<string>("");
  const [sourceLinkHash, setSourceLinkHash] = useState<string>("");

  useEffect(() => {
    const init = async () => {
      const hexUrl = await hashString(window.location.href);
      setSourceLinkHash(hexUrl);
      
      const commentRes = await sendMessage<CommentResponse[]>({
        action: "getComments",
        payload: hexUrl,
      });
      setComments(commentRes);

      const aliasRes = await sendMessage<string>({
        action: "getAlias",
        payload: null,
      });
      setAlias(aliasRes);
    };

    init();
  }, []);

  return { comments, userAlias, sourceLinkHash };
}