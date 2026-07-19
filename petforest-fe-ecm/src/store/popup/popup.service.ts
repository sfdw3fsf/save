import { PopupDTO } from './dto/popup.dto';
import { PopupRESP } from './response/popup.response';

export const popupRespToDto = (r: PopupRESP): PopupDTO | undefined => {
  return !r.isWatched
    ? {
        imageUrl: r.file.key ? process.env.NEXT_PUBLIC_S3_PREFIX + r.file.key : '',
      }
    : undefined;
};
