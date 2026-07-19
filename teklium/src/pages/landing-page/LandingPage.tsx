import PageContainer from '@/layouts/PageContainer';
import BackgroundImage from '@/assets/images/landing/image.png';
import PionearImage from '@/assets/images/landing/PioneerImage.png';
import CreatingImage from '@/assets/images/landing/CreatingImage.png';
import TestimonialCard from './components/TestimonialCard';
import Button from '@/components/button/Button';
export default function LandingPage() {
  return (
    <>
      <section
        className='h-screen w-full max-2xl:max-h-[760px] '
        style={{
          backgroundImage: `linear-gradient(to bottom, rgba(15, 15, 15, 0) 0%, rgba(15, 15, 15, 1) 230%), url(${BackgroundImage})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          backgroundRepeat: 'no-repeat',
          backgroundAttachment: 'scroll',
        }}>
        <div className='lg:px-84  px-4 flex flex-col gap-10 h-full justify-center items-center '>
          <p className='text-5xl text-gray-25 leading-[3.75rem] max-w-[768px] text-center font-bold '>
            Innovating eco-friendly
            <br />
            <span className='text-nowrap'>technologies to transform everyday</span>
            <br />
            <span className='text-nowrap'>living, working, and recreation.</span>
          </p>
          <div className='flex flex-row gap-3'>
            <Button variant='blue' className='w-[160px] h-[40px] '>
              Contact us
            </Button>
            <Button className='w-[187px] h-[40px] '>View Technologies</Button>
          </div>
        </div>
      </section>

      <section className='mt-[-1px] border-none flex flex-row justify-between bg-gray-950 pt-12 pb-24 px-48'>
        <h1 className='text-4xl text-gray-25 font-bold '>Who We Are</h1>
        <p className='text-xl text-gray-200 font-normal max-w-[443px]'>
          We are a seasoned team of scientists with extensive expertise in aerospace, mechanical engineering, energy, artificial
          intelligence, and semiconductor technology. Our team members have made significant contributions to advanced projects,
          including the F-35, the United States' most advanced jet fighter. With a proven track record of innovation and
          excellence in mechanical design, computer science, semiconductor technologies, and energy solutions, we are dedicated to
          pioneering advancements in these fields.
        </p>
      </section>

      <PageContainer>
        <div className='flex flex-col gap-16 pt-2 pb-12 '>
          {/* Section 1 */}
          <section className='flex flex-row lg:gap-[3.3125rem] '>
            {/* Left Part */}
            <div className='rounded-2xl w-full h-full py-[3.25rem] '>
              <img className=' ' src={PionearImage} alt='Pioneer Image'></img>
            </div>
            {/* Right Part */}
            <div className='max-w-[443px] flex flex-col gap-10'>
              <h1 className='text-6xl font-bold leading-[4.5rem] text-gray-800'>
                Pioneering the Future of Semiconductor Technologies
              </h1>
              <p className='text-gray-500 font-normal text-lg  '>
                At Teklium, we tackle the increasing demands of industrialization, economics, science, education, and politics
                with our groundbreaking carbon semiconductor technology. Unlike traditional silicon chips, our carbon
                semiconductors, developed in partnership with Nantero and Visual Link, deliver superior performance, power
                efficiency, and heat dissipation. Featuring innovations like multi-layered 3D semiconductor technology and Carbon
                Nanotubes (CNT), our chips offer extended lifespans and capabilities, ensuring efficient operation even under
                extreme conditions, with a lifespan of up to 100 years.
              </p>
            </div>
          </section>
          {/* Section 2*/}
          <section className='flex flex-row lg:gap-[3.3125rem] '>
            {/* Left Part */}
            <div className='max-w-[443px] flex flex-col gap-10'>
              <h1 className='text-6xl font-bold leading-[4.5rem] text-gray-800'>
                Creating Innovative Solutions for a Digital World
              </h1>
              <p className='text-gray-500 font-normal text-lg  '>
                With innovations such as the Reusable Chip, Memory-Mapped CPU, Quantum Communications, and Content Aware Memory,
                we are revolutionizing data center operations and AI applications, enhancing their speed, efficiency, and
                security. Our vision for a Super Internet, featuring Quantum Spooling and Atomic Routing, aims to transform global
                internet infrastructure with superior security and speed.
                <br />
                <br /> Additionally, our groundbreaking business strategies, including AI City and Chip Subscription, are paving
                new paths. AI City, supported by strategic partnerships, aims to improve broadband accessibility, data center
                efficiency, and sustainable economic development. Our Chip Subscription model allows users to access the latest
                advanced chips on a subscription basis, reducing electronic waste and ensuring they remain up to date with
                cutting-edge technology.
              </p>
            </div>

            {/* Right Part */}
            <div className='rounded-2xl w-full h-full py-[3.25rem] '>
              <img className=' ' src={CreatingImage} alt='Pioneer Image'></img>
            </div>
          </section>
          {/* Section 3 */}
          <section className='pt-12 flex flex-col gap-10'>
            {/* Title */}
            <div className='flex flex-col gap-3'>
              <h1 className='text-gray-950 font-bold text-4xl'>Who We Serve </h1>
              <p className='text-xl font-normal text-gray-500'>
                A wide range of industries and applications benefit from Teklium's advanced solutions
              </p>
            </div>
            {/* Testimonial */}
            <div>
              <TestimonialCard />
            </div>
          </section>
        </div>
      </PageContainer>
    </>
  );
}
