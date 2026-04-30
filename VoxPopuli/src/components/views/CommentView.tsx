import { useMemo, useState } from "react";
import type { CommentRequest, CommentResponse } from "../../contracts/Comment";
import { sendMessage } from "../../api/VoxPopuliApi";
import * as UI from "../Index";
import type { CommentMessage } from "../../types/MessageTypes";

type CommentNode = CommentResponse & {
  replies: CommentNode[];
};

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

  //utils
  const buildResponseTrees = (comments: CommentResponse[]): CommentNode[] => {
    if (!Array.isArray(comments)) return [];
    const map = new Map<string, CommentNode>();
    const roots: CommentNode[] = [];
    comments.forEach((comment) => {
      map.set(comment.commentId, { ...comment, replies: [] });
    });
    comments.forEach((comment) => {
      const commentNode = map.get(comment.commentId)!;
      if (comment.parentId) {
        const parent = map.get(comment.parentId);
        parent?.replies.push(commentNode);
      } else {
        roots.push(commentNode);
      }
    });

    return roots;
  };

  const orderTreesByNewest = (comments: CommentNode[]) => {
    return comments.sort((left, right) => {
      return (
        new Date(right.updatedAt).getTime() - new Date(left.updatedAt).getTime()
      );
    });
  };

  const buildCommentRequest = (
    parentId: string | undefined,
    content: string,
  ): CommentRequest => {
    return {
      parentId,
      content,
      alias: userAlias,
      sourceLinkHash: sourceLinkHash,
      updatedAt: new Date().toISOString(),
    };
  };

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
      buildCommentRequest(parentId, content),
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
