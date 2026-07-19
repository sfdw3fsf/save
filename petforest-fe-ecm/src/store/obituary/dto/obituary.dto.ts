import { UploadFile } from 'antd';
import { Dayjs } from 'dayjs';

type PetType = {
  id: number;
  name: string;
};

export type ObituaryDTO = {
  guardianInformation: {
    name: string;
    contact: string;
    funeralDate: string;
    branch: string;
    location: string;
  };
  petInformation: {
    name: string;
    petType: PetType;
    endDate: Dayjs | null;
    age: number;
    petBreed: PetType;
  };
  petImage: UploadFile[];
  obituaryFileIds?: UploadFile<any>[];
  isPaymentActivated: boolean;
};

export type ObituaryMessagesDTO = {
  id: number;
  author: string;
  content: string;
  createdAt: string;
};
