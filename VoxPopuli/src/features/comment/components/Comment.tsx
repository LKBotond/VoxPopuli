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
  return (
    <UI.Div className={`${baseClasses} ${className}`.trim()}>
      <UI.H>{`${comment.alias} says`}</UI.H>
      <UI.P>{comment.content}</UI.P>

      <UI.Button onClick={() => setShowReplyForm((status) => !status)}>
        {showReplyForm ? "Cancel" : "Reply"}
      </UI.Button>

      {showReplyForm && (
        <UI.CommentForm
          parentId={comment.commentId}
          handleComment={(parentId, content) => {
            onReply(parentId, content);
            setShowReplyForm(false);
          }}
        />
      )}

      {comment.replies?.map((reply) => (
        <Comment
          key={reply.commentId}
          comment={reply}
          onReply={onReply}
          className=" border-solid border-l-2 border-gray-200"
        />
      ))}
    </UI.Div>
  );
}
export default Comment;
