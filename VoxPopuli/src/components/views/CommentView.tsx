import type { CommentViewProps } from "../../types/Props";
import { buildResponseTrees } from "../../utils/comments";
import * as UI from "../Index";

export function CommentView({ comments }: CommentViewProps) {
  const roots = buildResponseTrees(comments);
  const handleRoot = async () => {
    //implementation eventually will come here
  };
  const handleReply = async (parentId: string) => {
    //implementation eventually will come here
  };

  return (
    <UI.Section className="grid-cols-5">
      <UI.Div className="col-span-3"></UI.Div>
      <UI.Div className="col-span-2 bg-gray-900">
        <UI.H>Vox Populi</UI.H>
        <UI.P>Let the people hear your voice</UI.P>
        <UI.CommentForm handleComment={handleRoot}></UI.CommentForm>

        {roots.map((rootNode) => (
          <UI.Comment
            key={rootNode.commentId}
            comment={rootNode}
            onReply={handleReply}
          />
        ))}
      </UI.Div>
    </UI.Section>
  );
}
