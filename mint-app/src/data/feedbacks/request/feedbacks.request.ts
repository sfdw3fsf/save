export type FeedbacksREQ = {
  projectId: number;
  title?: string;
  content?: string;
};

export type EditFeedbacksREQ = {
  feedbackId: number;
  title?: string;
  content?: string;
};
