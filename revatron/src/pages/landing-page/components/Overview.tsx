import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

import Drone from "/drone.svg";
import DroneShadow from "/drone_shadow.svg";

import Machine from "/machine.svg";
import MachineShadow from "/machine_shadow.svg";

import Mainboard from "/main_board.svg";
import MainboardShadow from "/main_board_shadow.svg";

import Rocket from "/rocket.svg";
import RocketShadow from "/rocket_shadow.svg";

import BgIamge from "/background_landing.svg";
function Overview() {
  const { t } = useTranslation();
  return (
    <div className='relative flex max-w-[1376px] items-center justify-center overflow-hidden'>
      {/* Set the image as the size reference */}
      <img src={BgIamge} alt='landing demo' className='min-h-[560px] w-full object-cover' />

      {/* Drone */}
      <motion.img
        animate={{
          y: [0, -10, 0], // Keyframes for "y" position: start at 0, go up to -20, back to 0
        }}
        transition={{
          duration: 5.5, // Time for one cycle
          repeat: Infinity, // Loop infinitely
          ease: "easeInOut", // Smooth easing
        }}
        src={Drone}
        className='absolute right-[2.6%] top-[7.45%] z-10 max-lg:max-w-[112px] max-md:right-[1.6%] max-md:top-[6.45%]'></motion.img>
      <img src={DroneShadow} className='absolute right-[4.28%] top-[16%] max-lg:max-w-[89px] max-md:top-[13%]'></img>

      {/* Machine */}
      <motion.img
        animate={{
          y: [-15, 0, -15], // Keyframes for "y" position: start at 0, go up to -20, back to 0
        }}
        transition={{
          duration: 5.5, // Time for one cycle
          repeat: Infinity, // Loop infinitely
          ease: "easeInOut", // Smooth easing
        }}
        src={Machine}
        className='absolute bottom-[69.69%] left-[9.95%] z-10 max-lg:bottom-[79.69%] max-lg:max-w-[141px] max-sm:left-[8.95%]'></motion.img>
      <img
        src={MachineShadow}
        className='absolute bottom-[66.65%] left-[11.87%] max-lg:bottom-[76.65%] max-lg:max-w-[93px]'></img>

      {/* Mainboard */}
      <motion.img
        animate={{
          y: [-10, 0, -10], // Keyframes for "y" position: start at 0, go up to -20, back to 0
        }}
        transition={{
          duration: 5.5, // Time for one cycle
          repeat: Infinity, // Loop infinitely
          ease: "easeInOut", // Smooth easing
        }}
        src={Mainboard}
        className='absolute left-[65.4%] top-[56.42%] z-10 max-lg:left-[75.4%] max-lg:top-[74.42%] max-lg:max-w-[255px] max-sm:left-[65.4%]'></motion.img>
      <img
        src={MainboardShadow}
        className='absolute left-[65.4%] top-[62.33%] max-lg:left-[75.4%] max-lg:top-[80.33%] max-lg:max-w-[255px] max-sm:left-[65.4%]'></img>
      {/* Rocket */}
      <motion.img
        animate={{
          y: [10, 0, 10], // Keyframes for "y" position: start at 0, go up to -20, back to 0
        }}
        transition={{
          duration: 5.5, // Time for one cycle
          repeat: Infinity, // Loop infinitely
          ease: "easeInOut", // Smooth easing
        }}
        src={Rocket}
        className='absolute right-[78.16%] top-[35.5%] z-10 max-lg:right-[88%] max-lg:top-[60%] max-lg:max-w-[115px] max-sm:right-[77%] max-sm:top-[70%]'></motion.img>
      <img
        src={RocketShadow}
        className='absolute right-[78.05%] top-[90.5%] max-lg:right-[88%] max-lg:max-w-[115px] max-sm:right-[77%] max-sm:top-[95%]'></img>
      {/* Overlay content */}
      <div className='absolute inset-0 flex flex-col items-center justify-center gap-3 px-4'>
        <motion.h1
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0 }}
          transition={{ duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='max-w-[768px] text-center text-4xl font-bold text-warm-950 max-md:text-2xl'>
          {t("overview_tittle")}
        </motion.h1>
        <motion.p
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0 }}
          transition={{ delay: 0.3, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='text-center text-lg font-normal text-warm-600 max-md:text-base'>
          {t("overview_content")}
        </motion.p>
      </div>
    </div>
  );
}

export default Overview;
