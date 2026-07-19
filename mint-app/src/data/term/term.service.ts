import { TermDTO } from './dto/term.dto';
import { TermRESP } from './response/term.response';

export const transformTermsResponse = (language: string, response?: TermRESP[]): TermDTO[] => {
  if (!response) return [];
  const isEn = language === 'en';
  return response.map((i) => ({
    id: i.id,
    name: isEn ? i.nameInEnglish : i.nameInKorean,
    description: isEn ? i.descriptionInEnglish : i.descriptionInKorean,
  }));
};
