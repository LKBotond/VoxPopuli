import type { CommentNode } from "../types/Props";
import type { CommentResponse } from "../contracts/Comment";
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
