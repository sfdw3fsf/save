import React from 'react';
import PageContainer from '@/layouts/PageContainer';
import BannerImage from '@/assets/images/about-us/StartImage.png';
import AuthorCard from './components/AuthorCard';
import InformationCard from './components/InformationCard';
export default function AboutUsPage() {
  return (
    <PageContainer
      title='about us'
      isTitlePart
      isSubtitlePart
      mainTitle='Teklium is the Global Leader'
      subTitle='About Us'
      detailTitle='We are dedicated to driving innovation and advancements in aerospace, mechanical engineering, energy, artificial intelligence, and semiconductor technology.
'>
      {/* Section 1 */}
      <section className='pt-4 lg:pb-24 pb-16'>
        <img src={BannerImage} alt='Banner Image'></img>
      </section>
      {/* Section 2 */}
      <section className='lg:pt-16 pt-6 flex flex-row lg:gap-20 gap-8  pb-16 border-b border-slate-200'>
        <div className=''>
          <AuthorCard />
        </div>
        <div className='flex flex-col gap-6 max-w-[598px]'>
          <h1 className='text-gray-950 text-3xl font-bold'>
            Recognized leader in semiconductor technology, specializes in Integrated Circuit (IC) Design with an emphasis on
            real-time AI technologies.
          </h1>
          <p className='text-gray-500 font-normal text-base'>
            Holding a master’s degree in electrical engineering from Pennsylvania State University and a Bachelor of Science from
            National Chiao Tung University, Taiwan, Jason has significantly contributed to pioneering projects such as the F-35
            Fighter Jet. He has developed advanced AI solutions for government projects and holds over 400 international patents
            in computer chip design, optic devices, computer security systems, and neural networks.
          </p>
          <InformationCard
            mainTitle={'Teklium Inc.'}
            subTitle={'Founder & CEO'}
            detail={
              'Jason introduced innovations like the reusable Cell Structured Computer Chip and near-frictionless mechanical batteries, revolutionizing the semiconductor and energy industries. His inventions have been adopted by leading companies, including Toyota, Nissan, and Intel. While serving as CEO of Mai Logic, Jason partnered strategically with IBM and Motorola, raising over $10 million and introducing several multi-million gate ICs.'
            }
          />
          <InformationCard
            mainTitle={'Revatron Co., Ltd'}
            subTitle={'Chief Technology Officer (CTO)'}
            detail={
              'Jason invented the Direct Object-Oriented Reality (DOORs) system technology, a real-time AI solution for machine learning and 3D reality modeling. In 2010, he developed Pathium Display Technology (PDT), a solution for displaying holographic videos on flat panel displays without 3D glasses.'
            }
          />
          <InformationCard
            mainTitle={'Harada International Law Firm'}
            subTitle={'Consultant'}
            detail={
              'Focusing on innovative solutions and international cooperation to advance Japan’s semiconductor industry. His strategic insights address technology-driven environmental challenges and market forces, leading to applications like mechanical batteries and reusable chips.'
            }
          />
        </div>
      </section>
      {/* Section 3*/}
      <section></section>
      {/* Section 4 */}
    </PageContainer>
  );
}
