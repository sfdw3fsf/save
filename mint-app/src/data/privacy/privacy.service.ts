import { PrivacyDTO } from './dto/privacy.dto';
import { PrivacyRESP } from './response/privacy.response';

export const transformPrivaciesResponse = (language: string, response?: PrivacyRESP[]): PrivacyDTO[] => {
  if (!response) return [];
  const isEn = language === 'en';
  return response.map((i) => ({
    id: i.id,
    name: isEn ? i.nameInEnglish : i.nameInKorean,
    description: isEn ? i.descriptionInEnglish : i.descriptionInKorean,
  }));
};
