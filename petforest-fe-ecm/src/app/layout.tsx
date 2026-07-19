import HTMLLayout from '@/layout/HTMLLayout';
import type { Metadata, Viewport } from 'next';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/slick.css';
import '../utils/styles/quill-snow.css';
import './globals.css';

export const metadata: Metadata = {
  title: '펫포레스트 반려동물장례식장 | Pet Forest',
  description:
    '반려동물 장례식장 펫포레스트 공식 홈페이지입니다. 반려동물 장례 절차, 장례비용 및 장례용품 등을 확인하실 수 있습니다. 함께한 순간을 영원히 기억합니다. Everlasting Memories.',
  openGraph: {
    type: 'website',
    url: 'https://pet-forest.wala-dev.com',
    description:
      '반려동물 장례식장 펫포레스트 공식 홈페이지입니다. 반려동물 장례 절차, 장례비용 및 장례용품 등을 확인하실 수 있습니다. 함께한 순간을 영원히 기억합니다. Everlasting Memories.',
    title: '펫포레스트 반려동물장례식장 | Pet Forest',
    images: [
      {
        url: 'https://pet-forest.wala-dev.com/images/openGraphImg.jpg',
      },
    ],
  },
};

export const viewport: Viewport = {
  initialScale: 1,
  maximumScale: 1,
  width: 'device-width',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <HTMLLayout>{children}</HTMLLayout>;
}
