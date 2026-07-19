import { POST_CATEGORIES } from '@/utils/constants/post/post.constant';

export type MainCategory = keyof typeof POST_CATEGORIES;
export type SubCategory = (typeof POST_CATEGORIES)[MainCategory];
