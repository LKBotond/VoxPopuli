import { useState, useEffect } from "react";
import type { CommentResponse } from "../../../shared/contracts/Comment";
import {
  getAllCommentsForPage,
  getUserAlias,
  getSourceLinkHash,
} from "../services/FrontendCommentService";
export function useVoxPopuliData() {
  const [comments, setComments] = useState<CommentResponse[]>([]);
  const [userAlias, setAlias] = useState<string>("");
  const [sourceLinkHash, setSourceLinkHash] = useState<string>("");

  useEffect(() => {
    const init = async () => {
      const urlHex = await getSourceLinkHash(window.location.href);

      const [commentRes, aliasRes] = await Promise.all([
        getAllCommentsForPage(urlHex),
        getUserAlias(),
      ]);
      setSourceLinkHash(urlHex);
      setComments(commentRes);
      setAlias(aliasRes);
    };

    init();
  }, []);

  return { comments, userAlias, sourceLinkHash };
}
