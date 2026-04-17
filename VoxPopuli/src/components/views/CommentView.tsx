import type { CommentViewProps } from "../../types/Props";
import { buildResponseTrees } from "../../utils/comments";
import Comment from "../uniques/Comment";
import * as UI from "../Index";
import type { CommentRequest } from "../../contracts/Comment";

export function CommentView({ comments }: CommentViewProps) {
  const roots = buildResponseTrees(comments);
  //const handleRoot = async () => {};
  //const handleReply = async (parentId: string) => {};

  return (
    <UI.Section className="grid-cols-5">
      <UI.Div className="col-span-3"></UI.Div>
      <UI.Div className="col-span-2 bg-gray-900">
        <UI.H>Vox Populi</UI.H>
        <UI.P>Let the people hear your voice</UI.P>
        {roots.map((rootNode) => (
          <Comment
            key={rootNode.commentId}
            comment={rootNode}
            onReply={handleReply}
          />
        ))}
      </UI.Div>
    </UI.Section>
  );
}
