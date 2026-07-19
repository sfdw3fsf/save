/* eslint-disable jsx-a11y/anchor-is-valid */
'use client';
import { useGlobalStore } from '@/data/global/global.store';
import { HEADER_SOCIALS } from '@/utils/constant/header/header-social.constant';
import { MY_ROUTE } from '@/utils/constant/route.constant';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import './footer.css';

const Footer = () => {
  const pathname = usePathname();
  const { isIntro } = useGlobalStore();

  return (
    <footer className={`py-[107px] bg-green-main text-white footer ${pathname === '/' && isIntro ? 'hidden' : 'block'}`}>
      <div className='container flex justify-between'>
        <div>
          <div className='flex items-center gap-4'>
            <div className='w-[44px] footer-logo'>
              <img src='/images/footer-award.png' alt='' />
            </div>
            <p className='text-[15px] font-medium leading-[1.44] footer-logo-title'>
              5년연속 소비자 선정 브랜드
              <br />
              대상 수상 (2020-2024)
            </p>
          </div>
          <ul className='footer-social-list'>
            {HEADER_SOCIALS.map((social, index) => (
              <li key={index}>
                <a href={social.link} target='_blank' rel='noreferrer'>
                  <img className='sns-green max-w-[35px] max-h-[23px]' src={social.img} alt='' />
                  <img className='sns-white max-w-[35px] max-h-[23px]' src={social.imgWhite} alt='' />
                </a>
              </li>
            ))}
          </ul>

          <div className='footer-detail mt-[34px]'>
            <div className='footer-detail-item'>
              <p className='text-[21px] font-bold company-name'>(주) 펫포레스트</p>
              <p className='text-[13px] font-normal opacity-80'>
                <Link href={MY_ROUTE.PRIVACY}>개인정보 처리방침</Link>&nbsp;&nbsp;I&nbsp;&nbsp;
                <Link href={MY_ROUTE.TERMS}>사이트 이용 약관</Link>
              </p>
            </div>
            <div className='footer-detail-item'>
              <p className='text-[12px]'>사업자 등록번호&nbsp;&nbsp;I&nbsp;&nbsp;255-81-00588</p>
              <p className='text-[13px]'>대표 이사&nbsp;&nbsp;I&nbsp;&nbsp;이상흥</p>
            </div>
            <div className='footer-detail-item footer-corp-detail'>
              <p className='text-[13px]'>업무문의&nbsp;&nbsp;I&nbsp;&nbsp;031-704-5171</p>
              <p className='text-[13px]'>예약 문의&nbsp;&nbsp;I&nbsp;&nbsp;1577-0996</p>
              <p className='text-[13px] footer-address'>고객 문의&nbsp;&nbsp;I&nbsp;&nbsp;pf@petforest.co.kr</p>
            </div>
            <div className='footer-detail-item'>
              <p className='text-[13px]'>Copyright Ⓒ PETFOREST Co.Ltd. All rights reserved.</p>
            </div>
          </div>
          <div className='mt-[23px] leading-[1]'>
            <a
              href='https://class.petforest.kr/main/index.jsp'
              target='_blank'
              className='btn-education-center footer-btn'
              rel='noopener noreferrer'>
              펫포레스트 교육센터 {'>'}
            </a>
          </div>
        </div>
        <div className='flex flex-col justify-end leading-[1] gap-[15px] btn-footer-branch-container'>
          <a href='/branch/branch-1' target='_blank' className='btn-footer-branch'>
            경기 광주점 알아보기 &gt;
          </a>
          <a href='/branch/branch-2' target='_blank' className='btn-footer-branch'>
            김포점 알아보기 &gt;
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
