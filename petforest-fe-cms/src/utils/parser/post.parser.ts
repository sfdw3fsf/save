import { CategoryDTO } from '@/store/post/dto/post.dto';
import { POST_CATEGORIES } from '../constants/post/post.constant';
import { POST_CATEGORIES as POST_CATEGORY_ENUM } from '../enums/post/post.enum';

export function parsePostCategory(value: POST_CATEGORY_ENUM): CategoryDTO {
  for (const [category, subCategories] of Object.entries(POST_CATEGORIES)) {
    if (subCategories === value) {
      return { category: category as keyof typeof POST_CATEGORIES, subCategory: null };
    }
    if (category === value) {
      return { category: value as keyof typeof POST_CATEGORIES, subCategory: null };
    }
    if (typeof subCategories === 'object' && Object.values(subCategories).includes(value)) {
      return { category: category as keyof typeof POST_CATEGORIES, subCategory: value };
    }
  }
}
