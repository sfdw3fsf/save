export type UploadFileRESP = {
  fileId: number;
  url: string;
};

export type FileRESP = {
  id: number;
  fileName: string;
  fileSize: number;
  memo: string | null;
  url: string;
  createdAt: string;
  key: string;
};
