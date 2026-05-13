import type { CommentResponse } from "../../../shared/contracts/Comment";
import type { CommentNode } from "../../../shared/types/Props";

export function buildResponseTrees(comments: CommentResponse[]): CommentNode[] {
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
}
export function orderTreesByNewest(comments: CommentNode[]) {
  return comments.sort((left, right) => {
    return (
      new Date(right.updatedAt).getTime() - new Date(left.updatedAt).getTime()
    );
  });
}
