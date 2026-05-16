import { useState } from "react";
import type { CommentResponse } from "../../../shared/contracts/Comment";
import * as UI from "../../../shared/UI";
type CommentNode = CommentResponse & {
  replies: CommentNode[];
};
type CommentProps = {
  className?: string | null;
  comment: CommentNode;
  onReply: (parentId: string | undefined, content: string) => void;
};
function Comment({ comment, onReply, className = "" }: CommentProps) {
  const baseClasses = "ml-1 p-2 pr-0 pb-0 min-w-0";
  const [showReplyForm, setShowReplyForm] = useState(false);
  const [showRepplies, setShowRepllies] = useState(false);

  return (
    <UI.Div className={`${baseClasses} ${className}`.trim()}>
      <UI.H>{`${comment.alias} says`}</UI.H>
      <UI.P>{comment.content}</UI.P>
      <UI.Div className="flex">
        <UI.Button onClick={() => setShowReplyForm((status) => !status)}>
          {showReplyForm ? "Cancel" : "Reply"}
        </UI.Button>
        <UI.Button onClick={() => setShowRepllies((status) => !status)}>
          {showRepplies ? "Hide" : "Show"}
        </UI.Button>
      </UI.Div>
      {showRepplies &&
        comment.replies?.map((reply) => (
          <Comment
            key={reply.commentId}
            comment={reply}
            onReply={onReply}
            className=" border-solid border-l-2 border-gray-200"
          />
        ))}

      {showReplyForm && (
        <UI.CommentForm
          parentId={comment.commentId}
          handleComment={(parentId, content) => {
            onReply(parentId, content);
            setShowReplyForm(false);
          }}
        />
      )}
    </UI.Div>
  );
}
export default Comment;
