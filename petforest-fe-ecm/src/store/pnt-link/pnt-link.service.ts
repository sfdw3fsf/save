import { orUndefined } from '@/utils/parser/io.parser';
import dayjs from 'dayjs';
import { PntDetailDTO, PntSettingListDTO } from './dto/pnt-link.dto';
import { PntDetailRESP, PntSettingListRESP } from './response/pnt-link.response';

export const pntSettingListRespToDto = (resp: PntSettingListRESP): PntSettingListDTO => {
  const { reservation, ...rest } = resp;
  let dto: PntSettingListDTO = {
    id: rest.id,
    key: rest.id,
    name: orUndefined(rest.name),
  };
  if (!reservation) return dto;
  const { funeralInformation, petInformation, guardianInformation, reservationPhoto } = reservation;
  dto = {
    ...dto,
    reservation: {
      id: reservation.id,
      key: reservation.id,
      code: orUndefined(reservation.code),
      funeralDate: funeralInformation?.funeralDate ? dayjs(+funeralInformation?.funeralDate) : null,
      funeralTime: orUndefined(funeralInformation?.funeralTime),
      petName: orUndefined(petInformation?.name),
      petBreed: petInformation?.petBreed
        ? { id: petInformation.petBreed.id, name: orUndefined(petInformation.petBreed.name) }
        : undefined,
      petImage: petInformation?.petImage
        ? {
            id: petInformation.petImage.id,
            key: petInformation.petImage.key,
            fileName: orUndefined(petInformation.petImage.fileName),
            url: process.env.NEXT_PUBLIC_S3_PREFIX + petInformation.petImage.key,
          }
        : undefined,
      guardianName: orUndefined(guardianInformation?.name),
      files: reservationPhoto?.files?.map((file) => ({
        id: file?.id,
        key: file?.key,
        fileName: orUndefined(file?.fileName),
        usedInMemorialRoom: Boolean(file?.usedInMemorialRoom),
        url: process.env.NEXT_PUBLIC_S3_PREFIX + file?.key,
      })),
    },
  };
  return dto;
};

export const pntSettingDetailRespToDto = (resp: PntDetailRESP): PntDetailDTO => {
  return {
    id: resp?.id,
    petName: resp.reservation.petInformation.name || '',
    breedName: resp.reservation.petInformation.petBreed?.name || '',
    guardianName: resp.reservation.guardianInformation.name || '',
    room: resp.name,
    files: resp.reservation.reservationPhoto.files?.map((file) => process.env.NEXT_PUBLIC_S3_PREFIX + file?.key),
  };
};
