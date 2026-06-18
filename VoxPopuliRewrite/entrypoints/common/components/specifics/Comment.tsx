import { useState } from "react";
import type { CommentResponse } from "../../contracts/Comment";
import * as UI from "../UI";
type CommentNode = CommentResponse & {
  replies: CommentNode[];
};
type CommentProps = {
  className?: string | null;
  comment: CommentNode;
  onReply: (parentId: string | undefined, content: string) => void;
  depth?: number;
};

//need to keeep track of depth and have a boolean flag for ascending and descending
const MAX_INDENT_DEPTH = 5;

function Comment({
  comment,
  onReply,
  className = "",
  depth = 0,
}: CommentProps) {
  const isIndented = depth < MAX_INDENT_DEPTH;

  const baseClasses = `p-2 pr-0 pb-0 min-w-0 ${isIndented ? "ml-4" : "ml-0"}`;
  const borderClass = isIndented ? "border-l-2 border-gray-200" : "border-l-0";
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
            depth={depth+1}
            className={borderClass }
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
