export type Attachment = {
  key: string;
  url: string;
  name: string;
  type: 'FILE' | 'IMAGE' | 'PDF' | 'EXCEL' | 'WORD' | 'CSV' | 'JPEG' | 'PNG';
  size: number;
};
