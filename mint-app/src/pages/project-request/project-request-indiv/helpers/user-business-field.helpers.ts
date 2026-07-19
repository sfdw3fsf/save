import { UserBusinessFieldCategory } from '@/data/user/response/user-details.response';

export const selectedUserBusinessFieldCategories = (businessFieldCategories: UserBusinessFieldCategory[]) => {
  const businessFieldsInKorean = businessFieldCategories
    .filter((category) => category.isUserSelected)
    .map((category) => category.name)
    .join(' - ');
  const businessFieldsInEnglish = businessFieldCategories
    .filter((category) => category.isUserSelected)
    .map((category) => category.nameInEnglish)
    .join(' - ');
  return { businessFieldsInKorean, businessFieldsInEnglish };
};
