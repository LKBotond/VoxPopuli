export interface CommentRequest {
  parentId: string;
  content: string;
  sourceLinkHash: string;
  updatedAt: string;
}

export interface CommentResponse {
  commentId: string;
  parentId: string;
  content: string;
  updatedAt: string;
}

export interface CommentEditRequest {
  commentId: string;
  editedContent: string;
}
