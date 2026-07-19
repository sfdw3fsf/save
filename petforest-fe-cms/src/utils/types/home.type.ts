import { ANNOUNCEMENT } from '../enums/home/home.enums';
import { InputEnum } from '../enums/input.enum';
import { ENUM_STATUS } from '../enums/status.enum';

export type FuneralDetails = {
  content: string;
  funeralNumber: string;
  status: ENUM_STATUS;
};

export type NoticeMNGT = {
  content: string;
  scheduleDate: string;
  funeral: FuneralDetails[];
  unaccompanied: FuneralDetails[];
  other: { content: string; period: string; status: ENUM_STATUS }[];
};

export type FieldContent = {
  label: string;
  containerClassName: string;
  header: string[];
  inputEnums: { type: InputEnum; options?: any; name: string; className: string }[];
  type: ANNOUNCEMENT;
};
