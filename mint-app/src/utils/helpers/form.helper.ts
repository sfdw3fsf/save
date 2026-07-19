export const formatKoreanPhoneNumber = (phoneNumber?: string): string => {
  if (!phoneNumber) {
    return '_';
  }
  if (phoneNumber.length === 11) {
    return phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
  }
  return phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
};

export const getNameByLanguage = (language: string, item: { name: string; nameInEnglish: string }) => {
  switch (language) {
    case 'en':
      return item.nameInEnglish;
    case 'ko':
    default:
      return item.name;
  }
};
