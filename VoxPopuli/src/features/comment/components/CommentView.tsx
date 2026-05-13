import { useMemo, useState } from "react";
import type {
  CommentRequest,
  CommentResponse,
} from "../../../shared/contracts/Comment";
import { sendMessage } from "../../../shared/api/frontend/Messaging";
import * as UI from "../../../shared/UI";
import type { CommentMessage } from "../../../shared/api/frontend/MessageTypes";
import {
  buildResponseTrees,
  orderTreesByNewest,
} from "../services/CommentService";
import { buildCommentRequest } from "../services/FrontendCommentService";

const baseClasses = "grid-cols-5 transition-all duration-200 ease-out";
interface CommentViewProps {
  comments: CommentResponse[];
  userAlias: string;
  sourceLinkHash: string;
  className?: string;
}

export function CommentView({
  comments,
  userAlias,
  sourceLinkHash,
  className = "",
}: CommentViewProps) {
  //hooks
  const [commentList, setCommentList] = useState<CommentResponse[]>(
    comments ?? [],
  );

  const buildCommentMessage = (request: CommentRequest): CommentMessage => {
    return {
      action: "comment",
      payload: request,
    };
  };

  const handleComment = async (
    parentId: string | undefined,
    content: string,
  ) => {
    const commentRequest = buildCommentMessage(
      buildCommentRequest(parentId, content, userAlias, sourceLinkHash),
    );
    const response: CommentResponse = await sendMessage(commentRequest);
    setCommentList((prev) => [...prev, response]);
  };

  const roots = useMemo(() => {
    return orderTreesByNewest(buildResponseTrees(commentList));
  }, [commentList]);

  return (
    <UI.Section className={`${baseClasses} ${className}`.trim()}>
      <UI.Div className="col-span-3"></UI.Div>
      <UI.Div className="col-span-2 p-4 bg-gray-900 overflow-y-auto overflow-x-hidden flex flex-col max-w-full ">
        <UI.H>Vox Populi</UI.H>
        <UI.P>Let the people hear your voice</UI.P>
        <UI.CommentForm
          handleComment={(parentId, content) => {
            handleComment(parentId, content);
          }}
        ></UI.CommentForm>

        {roots.map((rootNode) => (
          <UI.Comment
            key={rootNode.commentId}
            comment={rootNode}
            onReply={(parentId, content) => {
              handleComment(parentId, content);
            }}
          />
        ))}
      </UI.Div>
    </UI.Section>
  );
}
