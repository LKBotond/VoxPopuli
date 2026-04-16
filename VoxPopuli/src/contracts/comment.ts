export interface CommentRequest {
  parentId: string;
  content: string;
  alias : string
  sourceLinkHash: string;
  updatedAt: string;
}

export interface CommentResponse {
  commentId: string;
  parentId: string;
  alias : string
  content: string;
  updatedAt: string;
}

export interface CommentEditRequest {
  commentId: string;
  editedContent: string;
}
