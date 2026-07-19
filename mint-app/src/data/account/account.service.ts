import { ERROR_MESSAGE } from '@/utils/constants/data.constant';
import { AUTH_SOCIAL_PROVIDER } from '@/utils/enums/data/auth-social-provider.enum';
import { USER_ROLE } from '@/utils/enums/user/user-role.enum';
import { USER_STATUS } from '@/utils/enums/user/user-status.enum';
import { transformUserDetailsResponse } from '../user/user.service';
import { FieldCategoryDTO } from './dto/field-category.dto';
import { RegisterDTO } from './dto/register.dto';
import { SocialLoginDTO } from './dto/social-login.dto';
import { AdvisoryFieldCategoryRESP } from './response/advisory-field-category.response';
import { BusinessFieldCategoryRESP } from './response/business-field-category.response';
import { MedicalFieldCategoryRESP } from './response/medical-field-category.response';
import { RegisterRESP } from './response/register.response';
import { SocialLoginRESP } from './response/social-login.response';

export const transformSocialLoginResponse = ({ userAuthProvider, accessToken, ...userInfo }: SocialLoginRESP): SocialLoginDTO => {
  if (!(userAuthProvider.provider in AUTH_SOCIAL_PROVIDER)) {
    throw new Error(ERROR_MESSAGE.INVALID_AUTH_SOCIAL_PROVIDER);
  }

  return {
    accessToken,
    userAuthProvider,
    userInfo: transformUserDetailsResponse(userInfo),
  } as SocialLoginDTO;
};

const sortFields = (
  fields: {
    id: number;
    name: string;
    nameInEnglish: string;
    isOtherField: boolean;
  }[],
) => fields.sort((a, b) => (a.isOtherField === b.isOtherField ? 0 : a.isOtherField ? 1 : -1));

export const transformBusinessFieldCategoryResponse = (categories: BusinessFieldCategoryRESP[]): FieldCategoryDTO[] =>
  categories.map(({ name, id, nameInEnglish, businessFields: fields }) => ({
    id,
    name,
    nameInEnglish,
    fields: sortFields(fields),
  }));

export const transformAdvisoryFieldCategoryResponse = (categories: AdvisoryFieldCategoryRESP[]): FieldCategoryDTO[] =>
  categories.map(({ name, id, nameInEnglish, advisoryFields: fields }) => ({
    id,
    name,
    nameInEnglish,
    fields: sortFields(fields),
  }));

export const transformMedicalFieldCategoryResponse = (categories: MedicalFieldCategoryRESP[]): FieldCategoryDTO[] =>
  categories.map(({ name, id, nameInEnglish, medicalFields: fields }) => ({
    id,
    name,
    nameInEnglish,
    fields: sortFields(fields),
  }));

export const transformMedicalFieldCategoryToFlatItems = (categories: MedicalFieldCategoryRESP[]) => {
  return categories
    .flatMap((category) => category.medicalFields)
    .filter((item) => !item.isOtherField)
    .map(({ isOtherField, ...item }) => item);
};

export const transformRegisterResponse = (response: RegisterRESP): RegisterDTO => {
  if (!(response.userRole in USER_ROLE)) {
    throw new Error(ERROR_MESSAGE.INVALID_USER_ROLE);
  }

  if (!(response.userStatus in USER_STATUS)) {
    throw new Error(ERROR_MESSAGE.INVALID_USER_STATUS);
  }

  return response as RegisterDTO;
};
