import { languages } from '@/i18Config';
import { i18nRouter } from 'next-i18n-router';
import { NextRequest, NextResponse } from 'next/server';
import { i18nConfig } from './i18n/i18n.settings';
import { authRoutes, fullScreenModeRoutes, viewModeRoutes } from './utils/constants/route.constant';

export function middleware(request: NextRequest) {
  const currentUrl = request.nextUrl.pathname;
  const urlSegments = currentUrl.split('/');
  const filteredUrl = languages.includes(urlSegments[1]) ? `/${urlSegments.slice(2).join('/')}` : currentUrl;
  const isAuthRoute = authRoutes.includes(filteredUrl);
  const isViewModeRoute = viewModeRoutes.includes(filteredUrl);
  const isFullScreenModeRoute = fullScreenModeRoutes.includes(filteredUrl);

  const response = i18nRouter(request, i18nConfig);

  const isLogin = request.cookies.get('isLogin')?.value;
  const stay = request.cookies.get('stay')?.value;

  const token = !!request.nextUrl.searchParams.get('token');

  if (isLogin && isAuthRoute) {
    return NextResponse.redirect(new URL('/', request.url));
  }

  if (!stay && !isAuthRoute && !isLogin && !token && !isViewModeRoute && !isFullScreenModeRoute) {
    const redirectResponse = NextResponse.redirect(new URL('/ko/auth/login', request.url));
    return redirectResponse;
  }

  return response;
}

export const config = {
  matcher: ['/((?!api|_next/static|_next/image|assets|favicon.ico|sw.js|site.webmanifest|images/).*)'],
};
