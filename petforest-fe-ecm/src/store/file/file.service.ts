import { FileDTO } from './dto/file.dto';
import { FileRESP } from './response/file.response';

export const fileRespToDto = (r: FileRESP): FileDTO => {
  return {
    url: process.env.NEXT_PUBLIC_S3_PREFIX + r.key,
    fileName: r.fileName,
  };
};
