import { parseTimestampToDate, parseTimeStampToDayjs } from '@/utils/parser/date-time.parser';
import { orString } from '@/utils/parser/io.parser';
import { UploadFile } from 'antd';
import { ObituaryDTO, ObituaryMessagesDTO } from './dto/obituary.dto';
import { ObituaryUpdateREQ } from './request/obituary.request';
import { ObituaryMessagesRESP, ObituaryRESP } from './response/obituary.response';

export const obituaryRespToDto = (r: ObituaryRESP): ObituaryDTO => {
  return {
    guardianInformation: {
      name: orString(r.reservation.guardianInformation.name),
      contact: orString(r.reservation.guardianInformation.name),
      funeralDate:
        parseTimestampToDate(r.reservation.funeralInformation.funeralDate) ??
        '' + ' | ' + r.reservation.funeralInformation.funeralTime ??
        '',
      branch: r.reservation.branch.name,
      location: r.reservation.branch.roadAddress,
    },
    petInformation: {
      name: orString(r.reservation.petInformation.name),
      petType: orString(r.reservation.petInformation.petType),
      endDate: parseTimeStampToDayjs(r.reservation.petInformation.endDate),
      age: r.reservation.petInformation.age,
      petBreed: orString(r.reservation.petInformation.petBreed),
    },
    isPaymentActivated: r.isPaymentActivated,
    petImage: r.reservation.petInformation.petImage
      ? ([
          {
            uid: r.reservation.petInformation.petImage?.id.toString(),
            name: r.reservation.petInformation.petImage?.fileName,
            status: 'done',
            url: r.reservation.petInformation.petImage
              ? process.env.NEXT_PUBLIC_S3_PREFIX + r.reservation.petInformation.petImage?.key
              : '',
          },
        ] as UploadFile[])
      : [],
    obituaryFileIds: r.files.map(
      (item) =>
        ({
          uid: item.id.toString(),
          name: item.fileName,
          status: 'done',
          url: process.env.NEXT_PUBLIC_S3_PREFIX + item.key,
        }) as UploadFile,
    ),
  };
};

export const obituaryDtoToReq = (d: ObituaryDTO, fileIds?: number[], petImageId?: number[]): ObituaryUpdateREQ => {
  return {
    petImageId: petImageId && petImageId[0] ? +petImageId[0] : undefined,
    petName: d.petInformation.name,
    petTypeId: +d.petInformation.petType.id,
    endDate: d.petInformation.endDate?.valueOf(),
    petAge: +d.petInformation.age,
    petBreedId: d.petInformation.petBreed.id,
    obituaryFileIds: fileIds || [],
    isPaymentActivated: d.isPaymentActivated,
  };
};

export const obituaryMessagesRespToDto = (r: ObituaryMessagesRESP): ObituaryMessagesDTO => {
  return {
    ...r,
    createdAt: parseTimestampToDate(r.createdAt),
  };
};
